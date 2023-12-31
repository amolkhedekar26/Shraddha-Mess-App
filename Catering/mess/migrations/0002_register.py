# Generated by Django 3.0.4 on 2020-03-25 07:04

from django.db import migrations, models
import phonenumber_field.modelfields


class Migration(migrations.Migration):

    dependencies = [
        ('mess', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Register',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('email', models.EmailField(max_length=254, unique=True, verbose_name='email address')),
                ('first_name', models.CharField(max_length=50)),
                ('last_name', models.CharField(max_length=50)),
                ('mobile_no', phonenumber_field.modelfields.PhoneNumberField(help_text='Mobile number', max_length=128, region=None)),
            ],
        ),
    ]
