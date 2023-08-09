from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as DjangoUserAdmin
from django.utils.translation import ugettext_lazy as _
from . import models


# Register your models here.
@admin.register(models.CustomUser)
class UserAdmin(DjangoUserAdmin):
    """Define admin model for custom User model with no email field."""

    fieldsets = (
        (None, {'fields': ('email', 'password')}),
        (_('Personal info'), {'fields': ('first_name', 'last_name')}),
        (_('Permissions'), {'fields': ('is_active', 'is_staff', 'is_superuser',
                                       'groups', 'user_permissions')}),
        (_('Important dates'), {'fields': ('last_login', 'date_joined')}),
    )
    add_fieldsets = (
        (None, {
            'classes': ('wide',),
            'fields': ('email', 'password1', 'password2'),
        }),
    )
    list_display = ('email', 'first_name', 'last_name', 'is_staff')
    search_fields = ('email', 'first_name', 'last_name')
    ordering = ('email',)


admin.site.register(models.Register)
admin.site.register(models.January)
admin.site.register(models.February)
admin.site.register(models.March)
admin.site.register(models.April)
admin.site.register(models.May)
admin.site.register(models.June)
admin.site.register(models.July)
admin.site.register(models.August)
admin.site.register(models.September)
admin.site.register(models.October)
admin.site.register(models.November)
admin.site.register(models.December)
admin.site.register(models.Menu)
admin.site.register(models.PlatePrice)
admin.site.register(models.Profile)
