from django.urls import path
from .views import *

urlpatterns = [
    path('', home_page),
    path('login/', login_view),
    path('signup/', signup_view),
    path('logout/', logout_view),
    path('activate/<uidb64>/<token>/', activate),
    path('menu/', menu),
    path('attendance/', attendance),
    path('history/', history),
    path('save-attendance/', save_attenadance),
    path('menu/', menu),
    path('get-records/<month>/', get_records),
    path('manage-attendance/', manage_attendance),
    path('manage-menu/', manage_menu),
    path('manage-students/', manage_students),
    path('bill/', bill_page),
    path('get-bill/', get_bill),
    path('get-price/', get_price),
    path('get-absent/', get_absent),
    path('set-price/', set_price),
    path('manage-month-attendance/', manage_monthwise_attendance),
    path('get-admin-records/<month>/', get_admin_records),
    path('admin-save-attendance/', admin_save_attenadance),
    path('get-email-user-mapping/', get_email_user_mapping),
    path('get-admin-menu/<day>/', get_admin_menu),
    path('admin-save-menu/', admin_save_menu),
    path('admin-get-students/', admin_get_students),
    path('admin-save-students/', admin_save_students),
    path('sample/',sample)
]
