%post
sed -i '/^ExecStart=/iEnvironmentFile=-/etc/default/sshd-permitrootlogin' /usr/lib/systemd/system/sshd@.service
%end
