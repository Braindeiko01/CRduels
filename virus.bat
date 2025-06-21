@echo off
setlocal EnableDelayedExpansion

echo Buscando y eliminando carpetas vacías...

:loop
set "foundEmptyFolder=false"

for /f "delims=" %%D in ('dir /ad /b /s ^| sort /R') do (
    set "folder=%%D"
    set "isEmpty=true"
    
    for %%F in ("%%D\*") do (
        set "isEmpty=false"
    )

    if "!isEmpty!"=="true" (
        rd "%%D"
        echo Eliminada: %%D
        set "foundEmptyFolder=true"
    )
)

if "!foundEmptyFolder!"=="true" goto loop

echo Completado.
pause
