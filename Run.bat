REM %1% is the control file corresponding to the alternative year --> 2006.ctl
REM Note that this program requires Java Run Time (JRE) v1.6 or higher

java -version
java Modechanger_v3 %1%
java TrnBuild2PT_v4 %1%
