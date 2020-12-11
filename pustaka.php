<?php

define('server', 'localhost');
define('user', 'root');
define('password', '');
define('db', 'pustaka');

$con = mysqli_connect(server, user, password, db);

$query = "select * FROM kategori";
$sql = mysqli_query($con, $query);

$a = array();
while ($row = mysqli_fetch_array($sql)) {
    array_push($a, array(
        "id" => $row['id'],
        "kategori" => $row['kategori'],
    ));
}

echo json_encode($a);
mysqli_close($con);
