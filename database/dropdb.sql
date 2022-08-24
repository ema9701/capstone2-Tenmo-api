SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'tenmo';

DROP DATABASE tenmo;

DROP USER tenmo_owner;
DROP USER tenmo_appuser;