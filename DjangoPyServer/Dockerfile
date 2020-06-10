FROM python:3.8

ENV PYTHONUNBUFFERED 1

RUN mkdir -p /CoffeeBreakerDjangoPyServer/
WORKDIR /CoffeeBreakerDjangoPyServer/
COPY . /CoffeeBreakerDjangoPyServer/

RUN apt-get update && apt-get install -y apt-utils binutils libproj-dev gdal-bin

RUN pip install -r requirements.txt

CMD ["python", "manage.py", "runserver", "192.168.0.173:8000", "--insecure"]