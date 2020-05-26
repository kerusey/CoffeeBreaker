# Generated by Django 3.0.6 on 2020-05-25 21:12

import django.contrib.gis.db.models.fields
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('AdminMap', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='NodeLocation',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('geom', django.contrib.gis.db.models.fields.PointField(srid=4326)),
            ],
        ),
        migrations.DeleteModel(
            name='TestCBSpots',
        ),
        migrations.RenameField(
            model_name='coffeebreakerspot',
            old_name='yCoordinate',
            new_name='xCoord',
        ),
        migrations.RenameField(
            model_name='coffeebreakerspot',
            old_name='xCoordinate',
            new_name='yCoord',
        ),
    ]