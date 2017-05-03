#!/bin/bash

startX=0x00000203
startY=0x000006ca
endX=0x000002ab
endY=0x00000074
input touchscreen swipe $((startX)) $((startY)) $((endX)) $((endY)) 100

input touchscreen swipe $((0x00000203)) $((0x000006ca)) $((0x000002ab)) $((0x00000074)) 100