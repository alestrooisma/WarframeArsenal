<!DOCTYPE html>

<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include 'php/TableMaker.php';
include 'php/RangedWeapon.php';

try {
	$settings = parse_ini_file('settings.ini');
	
	$selection = $_GET['weapons'];
//	$i = 0;
//	while ($i < count($selection) && preg_match('[A-Za-z ]+', $selection[$i]) != 1) {
//		$i++;
//	}
	//TODO if count(selection) == 0
	$weaponlist = "('" . $selection[0] . "'";
	for ($i = 1; $i < count($selection); $i++) {
		$weaponlist .= ",'" . $selection[$i] . "'";
	}
	$weaponlist .= ')';
	
	$db = new PDO('mysql:host=localhost;dbname=arsenal', $settings['user'], $settings['password']);
	$statement = $db->query("SELECT * FROM RangedWeapons WHERE name IN " . $weaponlist);
	$statement->setFetchMode(PDO::FETCH_CLASS, 'RangedWeapon');
	$weapons = $statement->fetchAll();
	unset($db);
	
	$maker = new TableMaker($weapons);
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>

<html>
<head>
	<meta charset="utf-8">
	<title>Warframe weapons comparison</title>
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<!--<script src="jquery.js" async></script>-->
	<!--<script src="table.js" async></script>-->
</head>
<body>
	<div id="page">
		<div id="content">
<?php $maker->write('comptable') ?>
		</div>
	</div>
</body>
</html>