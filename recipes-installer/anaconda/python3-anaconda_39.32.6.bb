SUMMARY = "The anaconda package"
DESCRIPTION = "The anaconda package"
HOMEPAGE = "http://fedoraproject.org/wiki/Anaconda"
LICENSE = "GPL-2.0-only"
SECTION = "devel"

LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "gdk-pixbuf-native e2fsprogs gettext glade libxklavier rpm"

SRC_URI = "git://github.com/rhinstaller/anaconda.git;protocol=https;branch=fedora-39 \
           file://fedora.css \
           file://81-edit-sudoers.ks \
           file://81-systemd-preset-all.ks \
           file://81-add-env-file-for-sshd.ks \
           file://0001-do-not-build-po-and-doc.patch \
           file://0002-do-not-load-the-system-wide-Xresources.patch \
           file://0003-tweak-iso-mount-dir-and-kernel-name.patch \
           file://0004-customize-default-option-for-OE.patch \
           file://0007-dnfpayload.py-customize-for-OE.patch \
           file://0009-dynamic-detect-workable-locale.patch \
           file://0010-reboot-unconditionally.patch \
           file://0012-bootloader.py-Change-grub2-settings-to-match-oe-core.patch \
           file://0013-tweak-detect-kernel-version.patch \
           file://0016-kickstart-not-support-Authselect-Firewall-Firstboot.patch \
           file://0017-invisible-help-button.patch \
           file://0018-disable-non-implemented-functions.patch \
           file://0019-support-UEFI-boot.patch \
           file://0022-fix-quoted-empty-string-failed.patch \
           file://0023-do-not-support-ISO-hold-by-hard-drive-partitions.patch \
           file://0024-fix-cannot-stat-usr-share-gettext-gettext.h.patch \
           file://0025-tweak-bootloader-fs-type.patch \
           file://0026-support-timezone-setting.patch \
           file://0028-do-not-support-manually-set-time.patch \
           file://0030-detect-existence-of-Xorg.patch \
           file://0033-text-repository-setting-do-not-support-local-ISO-fil.patch \
           file://0034-text-repository-setting-support-http-proxy.patch \
           file://0036-do-not-support-network-setting-for-now.patch \
           file://0038-support-to-get-kickstart-from-network.patch \
           file://0039-support-authentication-for-kickstart.patch \
           file://0042-support-initramfs-boot.patch \
           file://0044-fix-installed-system-boot-from-encrypt-fs-failed.patch \
           file://0045-installation-destination-disable-iSCSI-network-disks.patch \
           file://0046-update-region-while-city-changes.patch \
           file://0048-fix-write-sysconfig-network-failed.patch \
           file://0049-pyanaconda-ui-gui-utils.py-tweak-mouse-arrow.patch \
           file://0052-invoke-grub-install-to-generate-efi-filesystem.patch \
           file://0053-do-not-support-closest-mirror.patch \
           file://0054-tweak-MAX_TREEINFO_DOWNLOAD_RETRIES.patch \
           file://0055-tweak-time-setting.patch \
           file://0056-Add-dracut-args-for-home-to-bootloader.patch \
           file://0057-do-not-customize-window-theme.patch \
           file://0058-tweak-product-short-name.patch \
           file://0060-tweak-shebang-of-bash.patch \
           file://0061-Tweak-label-name.patch \
           file://0062-Mount-var-volatile-during-install.patch \
           file://0063-update-fstab-by-appending.patch \
           file://0064-users.py-Fix-multilib-user-creation-error.patch \
           file://0065-bootloader.py-fix-UEFI-multilib-installation.patch \
           file://0066-fix-calling-grub-mkconfig-failed.patch \
           file://0068-not-install-chrony-unintentionally.patch \
           file://0070-remove-unsupported-icon-from-main-page-and-tweak-ico.patch \
           file://0071-disable-payloads-flatpak-ostree.patch \
           file://0072-fix-sshd-config.patch \
           file://0075-pyanaconda-payload-Fix-xserver-detection.patch \
           file://0076-limit-product-name-less-then-30-chars.patch \
           file://0078-anaconda.py-use-Fedora-as-default-product.patch \
           file://0079-find-live-os-image-for-oe.patch \
           file://0080-customize-live-image-for-oe.patch \
           file://0081-always-write-fstab-after-install.patch \
           file://0082-fix-grub-efi-boot-failure.patch \
           file://0083-reset-default-autopart-type.patch \
           "

SRCREV = "1a4aed721074ddcd84df88089024378c894ec65e"

S = "${WORKDIR}/git"

inherit autotools-brokensep features_check gettext gobject-introspection python3native pkgconfig
REQUIRED_DISTRO_FEATURES = "systemd x11 opengl"

EXTRA_OECONF += "--disable-selinux \
                 --disable-webui \
                 --with-sysroot=${PKG_CONFIG_SYSROOT_DIR} \
                 "

do_configure:prepend() {
    ( cd ${S}; STAGING_DATADIR_NATIVE=${STAGING_DATADIR_NATIVE} ${S}/autogen.sh --noconfigure)
}

do_install:append() {
    mkdir -p ${D}${datadir}/anaconda/post-scripts
    install -m 644 ${WORKDIR}/81-edit-sudoers.ks ${D}${datadir}/anaconda/post-scripts
    install -m 644 ${WORKDIR}/81-systemd-preset-all.ks ${D}${datadir}/anaconda/post-scripts
    install -m 644 ${WORKDIR}/81-add-env-file-for-sshd.ks ${D}${datadir}/anaconda/post-scripts
    install -m 644 ${S}/widgets/src/resources/*.svg ${D}${datadir}/anaconda/pixmaps
    install -m 644 ${S}/widgets/src/resources/*.png ${D}${datadir}/anaconda/pixmaps
    install -m 644 ${WORKDIR}/fedora.css ${D}${datadir}/anaconda/pixmaps
}

python __anonymous () {
    if not bb.utils.contains("PACKAGE_CLASSES", "package_rpm", True, False, d):
        raise bb.parse.SkipPackage('Anaconda requires RPM packages to be the default in PACKAGE_CLASSES.')
}

COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*)-(linux.*|freebsd.*)'
COMPATIBLE_HOST:armv7a = 'null'
COMPATIBLE_HOST:armv7ve = 'null'

FILES:${PN}-dbg += "${libexecdir}/anaconda/.debug ${PYTHON_SITEPACKAGES_DIR}/pyanaconda/.debug"
FILES:${PN}-staticdev += "${PYTHON_SITEPACKAGES_DIR}/pyanaconda/_isys.a"
FILES:${PN} = "\
               ${PYTHON_SITEPACKAGES_DIR}/pyanaconda \
               ${bindir} \
               ${datadir}/anaconda \
               ${datadir}/applications \
               ${datadir}/glade \
               ${datadir}/polkit-1 \
               ${datadir}/themes \
               ${libdir} \
               ${libexecdir} \
               ${sbindir} \
               ${sysconfdir} \
               "

PACKAGES += "${PN}-misc"
FILES:${PN}-misc = "/usr/lib"

RDEPENDS:${PN}-misc += "bash python3-core"

RDEPENDS:${PN} = "bash \
                  btrfs-tools \
                  ca-certificates \
                  chrony \
                  dmidecode \
                  dnf \
                  dosfstools \
                  e2fsprogs \
                  e2fsprogs-e2fsck \
                  e2fsprogs-mke2fs \
                  e2fsprogs-resize2fs \
                  e2fsprogs-tune2fs \
                  efibootmgr \
                  glibc-charmaps \
                  glibc-localedatas \
                  glibc-utils \
                  grub \
                  gzip \
                  iproute2 \
                  isomd5sum \
                  keybinder \
                  libarchive \
                  libgnomekbd \
                  libpwquality \
                  librsvg-gtk \
                  libtimezonemap \
                  libxkbcommon \
                  localedef \
                  mdadm \
                  network-manager-applet \
                  nfs-utils-client \
                  ntfs-3g \
                  ntfsprogs \
                  openssh \
                  procps \
                  python3-blivetgui \
                  python3-core \
                  python3-coverage \
                  python3-dasbus \
                  python3-langtable \
                  python3-libreport \
                  python3-meh \
                  python3-ntplib \
                  python3-ordered-set \
                  python3-productmd \
                  python3-pytz \
                  python3-requests-file \
                  python3-requests-ftp \
                  python3-simpleline \
                  python3-systemd \
                  python3-wrapt \
                  python3-zoneinfo \
                  rsync \
                  squashfs-tools \
                  tigervnc \
                  tzdata \
                  usermode \
                  util-linux \
                  util-linux-swaponoff \
                  util-linux-uuidgen \
                  xfsprogs \
                  xrandr \
                  "
