CHRONY_ANACONDA = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'anaconda-support', 'chrony_anaconda.inc', '', d)} \
"

require ${CHRONY_ANACONDA}
