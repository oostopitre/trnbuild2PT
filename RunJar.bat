REM %1% is the control file corresponding to the alternative year --> 2006.ctl
REM Note that this program requires Java Run Time (JRE) v1.6 or higher

java -version
java -jar Modechanger_v3.jar %1%
java -jar TrnBuild2PT_v4.jar %1%
