from datetime import datetime
from django.contrib.auth import get_user_model
from django.contrib.sites.shortcuts import get_current_site
from django.http import HttpResponse
from django.utils.encoding import force_bytes, force_text
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from rest_framework import status
from rest_framework.permissions import AllowAny, IsAuthenticated, IsAdminUser
from rest_framework.response import Response
from rest_framework.views import APIView
from mess.views import get_month, Menu
from .token import account_activation_token
from .serializer import *

User = get_user_model()


# Create your views here.
class SignupAPI(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        email = request.data["email"]
        password = request.data["password"]
        if User.objects.filter(email=email).exists():
            return Response({"Result": "User already exits"}, status=status.HTTP_409_CONFLICT)
        user = User.objects.create_user(email, password)
        user.is_active = False
        user.save()
        current_site = get_current_site(request)
        mytext = " \n" + user.email + "\n"
        text = "http://" + current_site.domain + "/activate/" + urlsafe_base64_encode(
            force_bytes(user.pk)) + "/" + account_activation_token.make_token(user) + "/"
        newtext = mytext + text
        import smtplib
        server = smtplib.SMTP('smtp.gmail.com', 587)
        server.ehlo()
        server.starttls()
        server.login("easycare.health.app@gmail.com", "intel@i5")
        server.sendmail("easycare.health.app@gmail.com", user.email, newtext)
        return Response({"Result": "Success"}, status=status.HTTP_201_CREATED)


def activate(request, uidb64, token):
    try:
        uid = force_text(urlsafe_base64_decode(uidb64))
        user = User.objects.get(pk=uid)
    except (TypeError, ValueError, OverflowError, User.DoesNotExist):
        user = None

    if user is not None and account_activation_token.check_token(user, token):
        user.is_active = True
        user.save()
        # login(request, user)
        return HttpResponse("Account Activated")
    else:
        return HttpResponse("Server Error")


class AttendanceAPIView(APIView):
    permission_classes = (IsAuthenticated,)

    def get(self, request, month):
        m = get_month(month.lower())
        obj = m.objects.filter(email=request.user.email)
        mydict = []
        for o in obj:
            form_date = datetime.strptime(str(o.date), "%Y-%m-%d").date()
            new_date = form_date.strftime("%B %d,%Y")
            m_name = form_date.strftime("%B")
            date = str(form_date.day) + "  " + m_name
            current_day = datetime.today().strftime("%d")
            current_month = str(datetime.today().strftime("%B"))
            record_day = form_date.strftime("%d")
            if current_month == m_name:
                if record_day > current_day:
                    continue
            d = {"date": date, "attendance": o.attendance}
            mydict.append(d)
        serializer = AttendanceSerializer(mydict, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        email = request.user.email
        d = request.data["date"]
        date = datetime.strptime(d, "%b %d, %Y").date()
        attendance = [int(request.data["noon"]), int(request.data["night"])]
        m = date.strftime("%B").lower()
        month = get_month(m)
        obj = month.objects.get(email=email, date=date)
        obj.attendance = attendance
        obj.save()
        return Response({"Result": "Success"})


class MenuAPIView(APIView):
    permission_classes = (AllowAny,)

    def get(self, request):
        obj = Menu.objects.all()
        serializer = MenuSerializer(obj, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
