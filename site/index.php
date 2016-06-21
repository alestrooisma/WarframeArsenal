<!DOCTYPE html>

<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include 'TableMaker.php';
include 'RangedWeapon.php';
try {
	$settings = parse_ini_file('settings.ini');	
	
	$db = new PDO('mysql:host=localhost;dbname=arsenal', $settings['user'], $settings['password']);
	$statement = $db->query('SELECT * from RangedWeapons');
	$statement->setFetchMode(PDO::FETCH_CLASS, 'RangedWeapon');
	$weapons = $statement->fetchAll();
//	$weapons = array();
//	foreach ($statement as $row) {
//		$weapons[] = $row;
//	}
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
	<link rel="stylesheet" type="text/css" href="main.css">
	<!--<script src="jquery.js" async></script>-->
	<!--<script src="table.js" async></script>-->
</head>
<body>
	<div id="page">
		<div id="content">
<?php
$maker->begin('comptable');
$maker->writeHeaderRow('', 'name');
$maker->writeRow('Mastery Level', 'mastery_level');
$maker->writeRow('Weapon Slot', 'slot');
$maker->writeRow('Weapon Type', 'type');
$maker->writeRow('Trigger Type', 'trigger_type');
$maker->writeRow('Fire Rate', 'fire_rate');
$maker->writeRow('Accuracy', 'accuracy');
$maker->writeRow('Magazine Size', 'mag_size');
$maker->writeRow('Max Ammo', 'max_ammo');
$maker->end();
?>
		</div>
	</div>
</body>
</html>