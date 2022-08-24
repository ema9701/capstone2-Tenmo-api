-- select * from transfers join account on transfers.account_from = account.account_id or (
-- transfers.account_to = account.account_id) join tenmo_user on account.user_id = tenmo_user.user_id 
-- where tenmo_user.username = 'user1' 
-- ORDER BY transfer_id DESC; 

-- select * from transfers; 



-- CREATE OR REPLACE FUNCTION fnprevent_update()
--   RETURNS trigger AS
-- $BODY$
--     BEGIN
--         RAISE EXCEPTION 'no way';
--     END;
-- $BODY$
--   LANGUAGE plpgsql VOLATILE
--   COST 100;



-- CREATE TRIGGER trg_prevent_update
--   BEFORE UPDATE OF dependency1, dependency2
--   ON topic
--   FOR EACH ROW
--   EXECUTE PROCEDURE fnprevent_update();