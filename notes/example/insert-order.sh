#!/usr/bin/env bash

#begin = 北京时间2018年1月01日0时0分0秒
#end   = 北京时间2018年1月20日0时0分0秒

http --timeout 600 POST 182.92.157.225:13080/mycar/order/1/ begin=1514736000000 end=1516377600000 rent_address="西直门" return_address="西直门" name="齐翔" identity="411502198804011111" phone="18811111111" bill="{\"发票人\":\"xxxx\"}" -pBHbh
