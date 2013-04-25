phonegap-android
================

This repository contains a sample app showing how to use Scanpay with Phonegap framework.
You will find the Scanpay plugin in src/it/scanpay/phonegap/plugins

Integration on a html view
-------------------------
If you are not familiar with Phonegap plugins please refer to [Phonegap documentation](http://docs.phonegap.com/en/2.6.0/index.html)

Like all plugins Scanpay plugins has to be called using 

    cordova.exec()
    
You should use the following as parameters :
service : "Scanpay"
action : "startScanpay"

The first parameter is mandatory, it represent the unique App id given when you created your account on [Scanpay]((https://scanpay.it)
The second parameter must be a boolean value indicating whether or not you wish to display Scanpay confirmation view.

Concerning the Android project make sure to follow the instructions given on our website.
