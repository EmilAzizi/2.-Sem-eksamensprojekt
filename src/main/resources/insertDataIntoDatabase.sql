INSERT INTO project (projectName, projectDescription, projectDate, ownerID)
VALUES('Wishlist', 'wishlist creator', '09/08/2000', 1);

INSERT INTO project (projectName, projectDescription, projectDate, ownerID)
VALUES('Delfinen', 'Administartive program', '10/12/2022', 2);

INSERT INTO users (userName, userPassword, projectID)
VALUES('emil_azizi', '1234', 1);

INSERT INTO users (userName, userPassword, projectID)
VALUES('mark_jessing', '1234', 2);

INSERT INTO projectStatus (statusName)
VALUES('Do');

INSERT INTO projectStatus (statusName)
VALUES('Doing');

INSERT INTO projectStatus (statusName)
VALUES('Done');
