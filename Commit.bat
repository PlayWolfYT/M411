@echo off

echo Welcome to the Git-Commit Tool by Ruben Martins

:addFileOrDir
set /p file="Please enter a directory or file you want to add to the commit: "
if exist %file% (
	rem File exists
	call git add %file%
	goto addAnother
) else (
	rem File does not exist
	echo This file does not exist. Please ensure that your path is valid
	PAUSE
	goto addFileOrDir
)

:addAnother
set /p another="Do you want to add another file? (Y/n) "
if /i "%another%"=="y" set add=1
if "%add%"=="1" (
	set add=0
	echo Adding another file
	goto addFileOrDir
) else (
	set add=0
	echo Not adding a file
	PAUSE
	EXIT
)

:commit
set /p commit=Please enter a message for the commit: 
call git commit -m %commit%
call git remote add origin https://github.com/PlayWolfYT/M411.git
call git push -u origin master
echo Files committed. Goodbye
PAUSE

PAUSE