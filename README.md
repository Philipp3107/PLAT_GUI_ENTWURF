# PLAT_GUI_ENTWURF
This is a GUI Project for an Application which purpose is to analyze log files.
This Application provides a simple and easy to understand User Interface to handle day to day tasks with LogFiles

It also provides a generic algorithm which functionality is determined by a specified Config file


## Future criterias

- Make usage of Method invoker
- Make use of lambdas
- make use of Predicate

### How to handle lines
- ```keep``` : original to reduced Logfile
- ```keepif``` : original to reduced Logfile if criteria is okay
- ```eliminate``` : not to reduced Logfile
- ```bridge``` : marks a section with a below or above Line, is a ```eleminate``` if no other line is specified
- ```userflow```: set seperate and is translated into Keys pressed on UI

## Questions
- how to invoke setter with args
- how to invode generic setter with args
- how to strcture Files when no specific variable is given (hashmap for key value invokation?)
