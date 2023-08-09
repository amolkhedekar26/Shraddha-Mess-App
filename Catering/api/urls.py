from django.urls import path
from rest_framework.authtoken.views import obtain_auth_token

from .views import *

urlpatterns = [
    path('signup/', SignupAPI.as_view()),
    path('obtain-auth-token/', obtain_auth_token),
    path('save-attendance/', AttendanceAPIView.as_view()),
    path('get-attendance/<month>/', AttendanceAPIView.as_view()),
    path('get-menu/', MenuAPIView.as_view())
]
