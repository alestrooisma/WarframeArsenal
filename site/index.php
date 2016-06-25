<!DOCTYPE html>

<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

try {
	$settings = parse_ini_file('settings.ini');
	$db = new PDO('mysql:host=localhost;dbname=arsenal', $settings['user'], $settings['password']);
	$statement = $db->query('SELECT name from RangedWeapons ORDER BY name');
	$weapons = $statement->fetchAll();
	unset($db);
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
			<form id="rangedform" name="rangedform" method="get" action="compare.php">
				<select name="weapons[]" size="10" multiple>
					<?php foreach ($weapons as $weapon): ?>
						<option value="<?= $weapon[0] ?>"><?= $weapon[0] ?></option>
					<?php endforeach; ?>
				</select>
				<br />
				<input type="submit" value="Compare">
			</form>
		</div>
	</div>
</body>
</html>