import json
from datetime import datetime
from django.contrib.auth.decorators import login_required
from django.contrib.sites.shortcuts import get_current_site
from django.http import HttpResponse
from django.shortcuts import render, redirect
from django.utils.encoding import force_text, force_bytes
from django.utils.http import urlsafe_base64_decode, urlsafe_base64_encode
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import (
    authenticate,
    get_user_model,
    login,
    logout
)
from django.apps import apps
from .models import *
from .token import account_activation_token
from .forms import *

User = get_user_model()


def home_page(request):
    return render(request, 'index.html')


def signup_view(request):
    if request.method == 'POST':
        form = SignUpForm(request.POST)
        if form.is_valid():
            email = request.POST.get("email")
            if Register.objects.filter(email=email).exists():
                reg_user = Register.objects.get(email=email)
                user = form.save(commit=False)
                user.is_active = False
                user.first_name = reg_user.first_name
                user.last_name = reg_user.last_name
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
                return redirect('/login/')
            else:
                form = SignUpForm(request.POST)
                form.errors["register"] = "Sorry! You are not yet registered."
        else:
            form = SignUpForm(request.POST)

    else:
        form = SignUpForm()
    return render(request, 'signup.html', {'forms': form})


def get_month(key):
    m = apps.all_models["mess"]
    return m[key]


def get_month_days(i):
    m_31 = list(range(1, 32))
    m_30 = list(range(1, 31))
    m_29 = list(range(1, 30))
    m_28 = list(range(1, 29))
    if i == 'january':
        l = m_31
    if i == 'february':
        l = m_28
    if i == 'march':
        l = m_31
    if i == 'april':
        l = m_30
    if i == 'may':
        l = m_31
    if i == 'june':
        l = m_30
    if i == 'july':
        l = m_31
    if i == 'august':
        l = m_31
    if i == 'september':
        l = m_30
    if i == 'october':
        l = m_31
    if i == 'november':
        l = m_30
    if i == 'december':
        l = m_31
    return len(l)


def register(mymail):
    m = apps.all_models["mess"]
    n = {}
    n['january'] = m['january']
    n['february'] = m['february']
    n['march'] = m['march']
    n['april'] = m['april']
    n['may'] = m['may']
    n['june'] = m['june']
    n['july'] = m['july']
    n['august'] = m['august']
    n['september'] = m['september']
    n['october'] = m['october']
    n['november'] = m['november']
    n['december'] = m['december']
    m_31 = list(range(1, 32))
    m_30 = list(range(1, 31))
    m_29 = list(range(1, 30))
    m_28 = list(range(1, 29))
    for i in m:
        if i == 'january':
            for d in m_31:
                m[i].objects.get_or_create(email=mymail, date="2020-1-" + str(d), attendance=[1, 1], plates=0)
        if i == 'february':
            for d in m_28:
                m[i].objects.get_or_create(email=mymail, date="2020-2-" + str(d), attendance=[1, 1], plates=0)
        if i == 'march':
            for d in m_31:
                m[i].objects.get_or_create(email=mymail, date="2020-3-" + str(d), attendance=[1, 1], plates=0)
        if i == 'april':
            for d in m_30:
                m[i].objects.get_or_create(email=mymail, date="2020-4-" + str(d), attendance=[1, 1], plates=0)
        if i == 'may':
            for d in m_31:
                m[i].objects.get_or_create(email=mymail, date="2020-5-" + str(d), attendance=[1, 1], plates=0)
        if i == 'june':
            for d in m_30:
                m[i].objects.get_or_create(email=mymail, date="2020-6-" + str(d), attendance=[1, 1], plates=0)
        if i == 'july':
            for d in m_31:
                m[i].objects.get_or_create(email=mymail, date="2020-7-" + str(d), attendance=[1, 1], plates=0)
        if i == 'august':
            for d in m_31:
                m[i].objects.get_or_create(email=mymail, date="2020-8-" + str(d), attendance=[1, 1], plates=0)
        if i == 'september':
            for d in m_30:
                m[i].objects.get_or_create(email=mymail, date="2020-9-" + str(d), attendance=[1, 1], plates=0)
        if i == 'october':
            for d in m_31:
                m[i].objects.get_or_create(email=mymail, date="2020-10-" + str(d), attendance=[1, 1], plates=0)
        if i == 'november':
            for d in m_30:
                m[i].objects.get_or_create(email=mymail, date="2020-11-" + str(d), attendance=[1, 1], plates=0)
        if i == 'december':
            for d in m_31:
                m[i].objects.get_or_create(email=mymail, date="2020-12-" + str(d), attendance=[1, 1], plates=0)


def login_view(request):
    next = request.GET.get('next')
    form = LoginForm(request.POST or None)
    if form.is_valid():
        username = form.cleaned_data.get('username')

        password = form.cleaned_data.get('password')

        user = authenticate(username=username, password=password)

        login(request, user)
        if next:
            return redirect(next)
        return redirect('/')

    context = {
        'form': form,
    }
    return render(request, "login.html", context)


def logout_view(request):
    logout(request)
    return redirect('/')


def activate(request, uidb64, token):
    try:
        uid = force_text(urlsafe_base64_decode(uidb64))
        user = User.objects.get(pk=uid)
    except (TypeError, ValueError, OverflowError, User.DoesNotExist):
        user = None

    if user is not None and account_activation_token.check_token(user, token):
        profile = Profile.objects.get(user=user)
        reg_user = Register.objects.get(email=user.email)
        profile.mess_number = reg_user.id
        profile.mob_no = reg_user.mobile_no
        profile.save()
        user.is_active = True
        user.save()
        register(user.email)
        # login(request, user)
        return HttpResponse("Account Activated")
    else:
        return HttpResponse("Server Error")


def menu(request):
    obj = Menu.objects.all()
    return render(request, 'menu.html', {"menu": obj})


@login_required(login_url='/login/')
def attendance(request):
    return render(request, 'attendance.html')


@login_required(login_url='/login/')
def history(request):
    months = ['January', 'February', 'March', 'April', 'May', 'June', 'July',
              'August', 'September', 'October', 'November', 'December']
    current_month = datetime.now().strftime("%B")
    current_index = months.index(current_month)
    sliced_months = months[:current_index + 1]
    return render(request, 'history.html',
                  {"months": sliced_months, "current_month": current_month})


@csrf_exempt
def save_attenadance(request):
    email = request.user.email
    d = request.POST["date"]
    date = datetime.strptime(d, "%d-%m-%Y").date()
    attendance = [int(request.POST["noon"]), int(request.POST["night"])]
    m = date.strftime("%B").lower()
    month = get_month(m)
    obj = month.objects.get(email=email, date=date)
    obj.attendance = attendance
    obj.save()
    return HttpResponse("Saved")


def get_records(request, month):
    m = get_month(month.lower())
    obj = m.objects.filter(email=request.user.email)
    mydict = []
    for o in obj:
        form_date = datetime.strptime(str(o.date), "%Y-%m-%d").date()
        m_name = form_date.strftime("%B")
        date = str(form_date.day) + "  " + m_name
        current_day = datetime.today().strftime("%d")
        current_month = str(datetime.today().strftime("%B"))
        record_day = form_date.strftime("%d")
        if current_month == m_name:
            if record_day > current_day:
                continue
        d = {"date": str(date), "attendance": o.attendance}
        mydict.append(d)
    ans = json.dumps(mydict)
    return HttpResponse(ans, content_type="application/json")


def manage_attendance(request):
    obj = User.objects.all()
    students = obj.exclude(email="amlu9421@gmail.com")
    return render(request, 'manage_attendance.html', {"students": students})


def manage_menu(request):
    days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
    current_day = datetime.today().strftime("%A")
    return render(request, 'manage_menu.html', {"days": days, "current_day": current_day})


def manage_students(request):
    students = Register.objects.all()
    return render(request, 'manage_students.html', {"students": students})


def bill_page(request):
    obj = User.objects.all()
    students = obj.exclude(email="amlu9421@gmail.com")
    months = ['January', 'February', 'March', 'April', 'May', 'June', 'July',
              'August', 'September', 'October', 'November', 'December']
    return render(request, 'bill.html', {"months": months, "students": students})


def get_price(request):
    price = PlatePrice.objects.all()
    price = price[0]
    price = {"price": price.price}
    price = json.dumps(price)
    return HttpResponse(price, content_type="application/json")


def collect_price():
    price = PlatePrice.objects.all()
    price = price[0]
    return price


@csrf_exempt
def set_price(request):
    price = request.POST["price"]
    p = PlatePrice.objects.all()
    p = p[0]
    p.price = int(price)
    p.save()
    return HttpResponse("Updated")


def get_absent(request):
    date = datetime.today()
    m_name = date.strftime("%B").lower()
    m = get_month(m_name)
    obj = m.objects.filter(date=date)
    afternoon = 0
    night = 0
    for o in obj:
        if o.attendance[0] == '0':
            afternoon += 1
        if o.attendance[1] == '0':
            night += 1
    result = {"afternoon": afternoon, "night": night}
    result = json.dumps(result)
    return HttpResponse(result, content_type="application/json")


@csrf_exempt
def get_bill(request):
    month = request.POST.get("month")
    email = request.POST.get("email")
    m_name = month.lower()
    m = get_month(m_name)
    if "@" in str(email):
        obj = m.objects.filter(email=email)
    else:
        profile = Profile.objects.get(mess_number=int(email))
        useremail = profile.user.email
        obj = m.objects.filter(email=useremail)
    afternoon = 0
    night = 0
    for o in obj:
        if o.attendance[0] == '0':
            afternoon += 1
        if o.attendance[1] == '0':
            night += 1
    absent = afternoon + night
    if absent > 8:
        absent = 8
    month_days = get_month_days(m_name) * 2
    days = month_days - absent
    price = collect_price()
    bill = days * price.price
    ans = {"absent": absent, "bill": bill}
    ans = json.dumps(ans)
    return HttpResponse(ans)


def manage_monthwise_attendance(request):
    months = ['January', 'February', 'March', 'April', 'May', 'June', 'July',
              'August', 'September', 'October', 'November', 'December']
    current_month = datetime.now().strftime("%B")
    current_index = months.index(current_month)
    sliced_months = months[:current_index + 1]
    email = request.GET.get("email")
    if "@" in email:
        user = User.objects.get(email=email)
    else:
        pr = Profile.objects.get(mess_number=email)
        user = User.objects.get(email=pr.user.email)
    request.session["student"] = user.email
    # user = User.objects.get(email=email)
    profile = Profile.objects.get(user=user)
    return render(request, 'manage_monthwise_attendance.html',
                  {"months": sliced_months, "current_month": current_month, "f_name": user.first_name,
                   "l_name": user.last_name, "mess_no": profile.mess_number})


def get_admin_records(request, month):
    m = get_month(month.lower())
    email = request.session["student"]
    obj = m.objects.filter(email=email)
    mydict = []
    for o in obj:
        form_date = datetime.strptime(str(o.date), "%Y-%m-%d").date()
        m_name = form_date.strftime("%b")
        date = str(form_date.day) + "  " + m_name
        current_day = datetime.today().strftime("%d")
        current_month = str(datetime.today().strftime("%b"))
        record_day = form_date.strftime("%d")
        if current_month == m_name:
            if record_day > current_day:
                continue
        d = {"date": str(date), "attendance": o.attendance}
        mydict.append(d)
    ans = json.dumps(mydict)
    return HttpResponse(ans, content_type="application/json")


@csrf_exempt
def admin_save_attenadance(request):
    email = request.POST["email"]
    d = request.POST["date"]
    date = datetime.strptime(d, "%d-%m-%Y").date()
    attendance = [int(request.POST["noon"]), int(request.POST["night"])]
    m = date.strftime("%B").lower()
    month = get_month(m)
    obj = month.objects.get(email=email, date=date)
    obj.attendance = attendance
    obj.save()
    return HttpResponse("Saved")


def get_email_user_mapping(request):
    md = []
    users = User.objects.all()
    users = users.exclude(email="amlu9421@gmail.com")
    for u in users:
        fn = u.first_name
        ln = u.last_name
        c = fn + " " + ln
        m = {"name": fn + " " + ln, "email": u.email}
        md.append(m)
    ans = json.dumps(md)
    return HttpResponse(ans, content_type="application/json")


def get_admin_menu(request, day):
    obj = Menu.objects.get(day__iexact=day)
    mydict = []
    d = {"day": obj.day, "afternoon": obj.afternoon, "night": obj.night}
    mydict.append(d)
    ans = json.dumps(mydict)
    return HttpResponse(ans, content_type="application/json")


@csrf_exempt
def admin_save_menu(request):
    data = request.POST["time"]
    day = request.POST["day"]
    flag = request.POST["flag"]
    obj = Menu.objects.get(day__iexact=day)
    if flag == "noon":
        obj.afternoon = data
        obj.save()
    if flag == "night":
        obj.night = data
        obj.save()
    return HttpResponse("Saved")


def admin_get_students(request):
    email = request.GET.get("email")
    mylist = []
    if "@" in email:
        user = Register.objects.get(email=email)
    else:
        user = Register.objects.get(id=int(email))
    mob = "+" + str(user.mobile_no.country_code) + str(user.mobile_no.national_number)
    d = {"email": user.email, "f_name": user.first_name, "l_name": user.last_name, "mob_no": mob}
    mylist.append(d)
    ans = json.dumps(mylist)
    return HttpResponse(ans, content_type="application/json")


@csrf_exempt
def admin_save_students(request):
    email = request.POST.get("s_email")
    f_name = request.POST.get("s_fname")
    l_name = request.POST.get("s_lname")
    mob_no = request.POST.get("s_mob")
    obj = Register.objects.get(email=email)
    obj.first_name = f_name
    obj.last_name = l_name
    obj.mobile_no = mob_no
    obj.save()
    return HttpResponse("Saved")


def sample(request):
    return render(request, 'ne.html')
