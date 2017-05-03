#!/bin/bash

EV_ABS=0x0003
EV_KEY=0x0001
EV_SYN=0x0000

ABS_MT_TRACKING_ID=0x0039
BTN_TOUCH=0x014a
ABS_MT_POSITION_X=0x0035
ABS_MT_POSITION_Y=0x0036
SYN_REPORT=0x0000

DOWN=0x0001
UP=0x0000

startX=$1
startY=$2

event_device="/dev/input/event10"

sendevent $event_device $((EV_ABS)) $(($ABS_MT_TRACKING_ID)) $((0x0000030e))
sendevent $event_device $((EV_KEY)) $(($BTN_TOUCH)) $(($DOWN))
sendevent $event_device $((EV_ABS)) $(($ABS_MT_POSITION_X)) $(($startX))
sendevent $event_device $((EV_ABS)) $(($ABS_MT_POSITION_Y)) $(($startY))
sendevent $event_device $((EV_SYN)) $(($SYN_REPORT)) $((0))
sendevent $event_device $((EV_ABS)) $(($ABS_MT_TRACKING_ID)) $((0xffffffff))
sendevent $event_device $((EV_KEY)) $(($BTN_TOUCH)) $(($UP))
sendevent $event_device $((EV_SYN)) $((SYN_REPORT)) $((0)) 
