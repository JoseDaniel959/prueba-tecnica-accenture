provider "aws" {
  region = var.region
}

data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-amd64-server-*"]
  }

  owners = ["099720109477"] # Canonical
}


#----------------------------------------------------
#VPC

# Creacion de la VPC donde irán los servicios de AWS
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.19.0"

  name = "vpc-accenture"
  cidr = "10.0.0.0/16"

  azs             = ["us-west-2a", "us-west-2b"]
  private_subnets = ["10.0.1.0/24", "10.0.2.0/24"]
  public_subnets  = ["10.0.101.0/24"]

  enable_nat_gateway = true
  enable_vpn_gateway = false
  single_nat_gateway = true

}
#----------------------------------------------------


#----------------------------------------------------
#GRUPOS DE SEGURIDAD

#Se configura Security groups
resource "aws_security_group" "web_sg" {
  name        = "web-sg"
  description = "Security grupo para ec2"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["38.156.230.139/32"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
  from_port   = 8080
  to_port     = 8080
  protocol    = "tcp"
  cidr_blocks = ["0.0.0.0/0"]
}
}

resource "aws_security_group" "rds_sg" {
  name        = "rds-sg"
  description = "Security group para RDS"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [aws_security_group.web_sg.id] # Permite 
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
#----------------------------------------------------

#----------------------------------------------------
#Key pair
resource "tls_private_key" "ec2_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}
resource "aws_key_pair" "ec2_key" {
  key_name   = "my-ec2-key"
  public_key = tls_private_key.ec2_key.public_key_openssh
}


resource "local_file" "ec2_private_key" {
  filename        = "${path.module}/my-ec2-key.pem"
  content         = tls_private_key.ec2_key.private_key_pem
  file_permission = "0600"
}
#----------------------------------------------------





#----------------------------------------------------
#EC2

#Creación de EC2
resource "aws_instance" "app_server" {
  ami                    = data.aws_ami.ubuntu.id
  instance_type          = "t3.micro"
  subnet_id              = module.vpc.public_subnets[0]
  vpc_security_group_ids = [aws_security_group.web_sg.id]
  associate_public_ip_address = true
  key_name = aws_key_pair.ec2_key.key_name
  tags = {
    Name = "spring-webflux"
  }

}
#----------------------------------------------------



#----------------------------------------------------
#RDS

#Creación de RDS
resource "aws_db_instance" "postgres" {
  allocated_storage      = 10
  db_name                = "DbPrueba"
  engine                 = "postgres"
  engine_version         = "16.15"
  instance_class         = "db.t4g.micro"
  username               = "postgres"
  password               = "12345678"
  identifier             = "my-rds-instance"
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  db_subnet_group_name   = aws_db_subnet_group.main.name
  skip_final_snapshot    = true
  
}


resource "aws_db_subnet_group" "main" {
  name        = "main-db-subnet-group"
  subnet_ids  = module.vpc.private_subnets
  description = "Main DB Subnet Group"
  tags = {
    Name = "DB Subnet Group"
  }
}
#----------------------------------------------------


output "ec2_public_ip" {
  value = aws_instance.app_server.public_ip
}

output "rds_endpoint" {
  value = aws_db_instance.postgres.endpoint
}

