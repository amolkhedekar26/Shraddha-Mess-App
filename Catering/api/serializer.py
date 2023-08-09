from rest_framework import serializers

from mess.models import Menu


class AttendanceSerializer(serializers.Serializer):
    date = serializers.CharField(max_length=20)
    attendance = serializers.ListField()


class MenuSerializer(serializers.Serializer):
    day = serializers.CharField(max_length=50)
    afternoon = serializers.CharField(max_length=100)
    night = serializers.CharField(max_length=100)
