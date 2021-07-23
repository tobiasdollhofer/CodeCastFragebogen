# CodeCastFragebogen
Plugin for study execution of codecast plugin. Bachelor thesis of Tobias Dollhofer (University of Regensburg 2021)

## Features
Plugin provides a tool window for opening questionaire and test of the study in default browser prefilling with a custom user id. Also this plugin logs events in the editor to a csv in user home directory. These events containing the caret position, selected code as well as opening or closing files. By clicking the button to start test in the end of the study the logs will be uploaded to a configured ftp server.

## Used libraries
- IntelliJ Plugin SDK
- OpenCsv

## Packages

### log
contains listener classes and csv logger for writing and uploading log files

### ui
contains ViewFactory and the actual view files

### util
contains config file with constants and Listener for opening a project

### uuid
contains a helper class to provide a user id as well as a session id

This plugin was designed and tested for IntelliJ and Android Studio Version 2021.x. 