from django.db import models
from django.contrib.auth.models import AbstractUser
from django.db.models import CharField
from django.db.models.signals import post_save
from django.dispatch import receiver
from django.utils.timezone import now
from django.utils.translation import ugettext_lazy as _
from django_mysql.models import ListCharField
from phonenumber_field.modelfields import PhoneNumberField
from .managers import CustomUserManager


class CustomUser(AbstractUser):
    username = None
    email = models.EmailField(_('email address'), unique=True)

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []

    objects = CustomUserManager()

    def __str__(self):
        return self.email


class Profile(models.Model):
    user = models.OneToOneField(CustomUser, on_delete=models.CASCADE)
    mess_number = models.IntegerField(null=True)
    address = models.CharField(max_length=100, blank=True)
    mob_no = PhoneNumberField(blank=True, help_text="Mobile number")

    def __str__(self):
        return self.user.email


@receiver(post_save, sender=CustomUser)
def update_user_profile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)
        instance.profile.save()


class Register(models.Model):
    email = models.EmailField(_('email address'), unique=True)
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=50)
    mobile_no = PhoneNumberField(blank=False, help_text="Mobile number")

    def __str__(self):
        return str(self.id) + self.first_name + " " + self.last_name


class January(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class February(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class March(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class April(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class May(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class June(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class July(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class August(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class September(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class October(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class November(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class December(models.Model):
    email = models.EmailField(max_length=50)
    date = models.DateField(default=now)
    attendance = ListCharField(
        base_field=CharField(max_length=10),
        size=6,
        max_length=(6 * 11))
    plates = models.IntegerField()

    def __str__(self):
        return self.email + " " + str(self.date)


class Menu(models.Model):
    day = models.CharField(max_length=50)
    afternoon = models.CharField(max_length=100)
    night = models.CharField(max_length=100)

    def __str__(self):
        return self.day


class PlatePrice(models.Model):
    price = models.IntegerField()

    def __str__(self):
        return str(self.price)
