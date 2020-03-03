@echo off

echo delete old app
if exist .\libs\example.exe (
    del .\libs\example.exe
)

rem add mingw to the path
set orig_path=%PATH%
set PATH=%PATH%;C:\tools\mingw64\bin

echo compile it
gcc -o example.exe -L.\libs src\test\c\test.c -lumfpack

if exist example.exe (
    move example.exe .\libs

    echo test app
    .\libs\example.exe
)

rem reset the path
set PATH=%orig_path%

echo all done
