SUMMARY = "A text UI framework"
DESCRIPTION = "This is a text UI framework originally which \
was a part of the Anaconda installer project."
HOMEPAGE = "https://github.com/rhinstaller/python-simpleline"
SECTION = "devel"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=8350171b4e0d605dd7f117e75eb5b4cd"

SRC_URI = "git://github.com/rhinstaller/python-simpleline;protocol=https;branch=main"
SRCREV = "cf7a049623432acbdb8ec23eeadbfa666761e109"
S = "${WORKDIR}/git"

inherit setuptools3_legacy
