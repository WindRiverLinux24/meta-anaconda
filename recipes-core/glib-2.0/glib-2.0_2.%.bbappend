FILESEXTRAPATHS:prepend:anaconda := "${THISDIR}/glib-2.0:"
SRC_URI:append:anaconda = " \
    file://0001-fix-anaconda-timezone-crash.patch \
"
