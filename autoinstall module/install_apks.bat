@echo off

:: ����adb ��֤��˳������
adb kill-server
adb start-server

adb wait-for-device

:: ����ApkPathĿ¼�µ��ļ������а�װ
:: %cd% ��ʾ��ǰĿ¼������ű�������ǵ�ǰĿ¼�� APKs��Ŀ¼�µ��ļ�
:: ���Ը����Լ��������޸�

set ApkPath=%cd%\APKs
cd %ApkPath%

for /R %%s in (.,*) do (
	::Ҫʹ������������apk��·������Ȼadb install�﷨����
	adb install "%%s"
)

:: ִ���꣬cmd��������
pause