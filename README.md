# PortScan 

JRE 1.7 or 1.8 required

Help :
 
Usage: java com.jp.PortScanPlus [host] [startPort] [endPort] 

Usage: java com.jp.ParallelPortScan [host] [startPort] [endPort] 


Examples of commands :  

To perform a sequential scan 
```
    java com.jp.PortScanPlus localhost 
    java com.jp.PortScanPlus www.google.com 443 443 
```

To perform a parallel scan 
```
    java com.jp.ParallelPortScan localhost 
    java com.jp.ParallelPortScan www.google.com 443 443 
```

Example of execution :
```
java com.jp.PortScanPlus localhost
Scanning host localhost
22  
25  
111  
443 SSL Thu Jan 23 10:42:26 CET 2020 
1315  
1501  
1527  
1950  
1964  
2315  
2809  
5666  
6010  
6011  
8081 HTTP 
8880 SSL Sun May 06 21:30:50 CEST 2018 
9000 HTTP 
9001  
9044 SSL Sun May 06 21:30:50 CEST 2018  SECURED WAS ADMIN
9100  
9143 HTTP 
9144 HTTP 
9145 SSL Sun May 20 21:30:46 CEST 2018 
9146 SSL Sun May 20 21:30:46 CEST 2018  SECURED WAS ADMIN
9147  
9148 SSL Sun May 20 21:30:46 CEST 2018 
9149 SSL Sun May 20 21:30:46 CEST 2018 
9151 SSL Sun May 20 21:30:46 CEST 2018 
9152  
9153  
9404  
9405 SSL Sun May 06 21:30:50 CEST 2018 
9443 SSL Sun May 06 21:30:50 CEST 2018 
9633 SSL Sun May 06 21:30:50 CEST 2018 
10005  
10080 HTTP 
10443  
13194  
13205  
```


