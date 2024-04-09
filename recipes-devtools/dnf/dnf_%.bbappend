FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:anaconda = " file://0001-Revert-Does-not-print-Verify-package-RhBug-1908253.patch"
