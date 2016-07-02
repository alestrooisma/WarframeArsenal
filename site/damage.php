<!DOCTYPE html>
<?php
	include 'php/DamageTableHelpers.php';
?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>WarframeArmory - Damage Types</title>
		<link rel="stylesheet" type="text/css" href="css/damage.css">
    </head>
    <body>
		<table class="damagetable">
			<tbody>
				<tr>
					<td style="width:10px;"></td>
					<?=' <th colspan="2">Surface:</th>'?>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Flesh" title="Flesh">Flesh</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Cloned_Flesh" title="Cloned Flesh">Cloned</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Fossilized" title="Fossilized">Fossil</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Infested" title="Infested">Infested</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Infested_Flesh" title="Infested Flesh">I. Flesh</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Infested_Sinew" title="Infested Sinew">Sinew</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Machinery" title="Machinery">Machine</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Robotic" title="Robotic">Robotic</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Object" title="Object">Object</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Shield" title="Shield">Shield</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Proto_Shield" title="Proto Shield">Proto</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Ferrite_Armor" title="Ferrite Armor">Ferrite</a></th>
					<th><a href="http://warframe.wikia.com/wiki/Damage_2.0/Alloy_Armor" title="Alloy Armor">Alloy</a></th>
					<th>Status</th>
				</tr>
				<tr class="separator">
					<td rowspan="4" class="rowheader">Physical<br>Damage</td>
					<?php writeRowHeader("Impact") ?>
					<td><span style="color: red;">-25%</span></td>
					<td><span style="color: red;">-25%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+50%</span></td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td></td>
					<td><sup>1)2)</sup>Stagger</td>
				</tr>
				<tr>
					<?php writeRowHeader("Puncture") ?>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td><span style="color: red;">-20%</span></td>
					<td><span style="color: red;">-50%</span></td>
					<td><span style="color: green;">+50%</span></td>
					<td><span style="color: green;">+15%</span></td>
					<td>Weaken</td>
				</tr>
				<tr>
					<?php writeRowHeader("Slash") ?>
					<td><span style="color: green;">+25%</span></td>
					<td><span style="color: green;">+25%</span></td>
					<td><span style="color: green;">+15%</span></td>
					<td><span style="color: green;">+25%</span></td>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-25%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-15%</span></td>
					<td><span style="color: red;">-50%</span></td>
					<td>Bleed</td>
				</tr>
				<tr>
					<?php writeRowHeader("Finishing") ?>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+33%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td><sup>5)</sup><span style="color: grey;">N/A</span>&nbsp;&nbsp;</td>
					<td><sup>5)</sup><span style="color: grey;">N/A</span>&nbsp;</td>
					<td><sup>5)</sup><span style="color: grey;">N/A</span></td>
					<td><sup>5)</sup><span style="color: grey;">N/A</span></td>
					<td>-</td>
				</tr>
				<tr class="separator">
					<td rowspan="4" class="rowheader">Elemental<br>Damage</td>
					<?php writeRowHeader("Cold") ?>
					<td></td>
					<td></td>
					<td><span style="color: red;">-25%</span></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+25%</span></td>
					<td> Slowdown</td>
				</tr>
				<tr>
					<?php writeRowHeader("Electricity") ?>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+50%</span></td>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td> Chain Attack<br><sup>1)2)</sup>Stun</td>
				</tr>
				<tr>
					<?php writeRowHeader("Heat") ?>
					<td></td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td><span style="color: green;">+25%</span></td>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td></td>
					<td></td>
					<td> Fire DoT<br><sup>1)2)3)</sup>Panic</td>
				</tr>
				<tr>
					<?php writeRowHeader("Toxin") ?>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-25%</span></td>
					<td><span style="color: red;">-25%</span></td>
					<td></td>
					<td> <sup>5)</sup><span style="color: grey;">N/A</span>&nbsp;&nbsp;</td>
					<td> <sup>5)</sup><span style="color: grey;">N/A</span>&nbsp;</td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td> Health DoT</td>
				</tr>
				<tr class="separator">
					<td rowspan="6" class="rowheader">Combined<br>Elemental Damage</td>
					<?php writeCombinedRowHeader('Blast', 'Heat', 'Cold') ?>
					<td></td>
					<td></td>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td><span style="color: green;">+75%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-25%</span></td>
					<td></td>
					<td> <sup>1)2)</sup>Knockdown</td>
				</tr>
				<tr>
					<?php writeCombinedRowHeader('Corrosive', 'Electricity', 'Toxin') ?>
					<td></td>
					<td></td>
					<td><span style="color: green;">+75%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td><span style="color: green;">+75%</span></td>
					<td></td>
					<td> -Armor</td>
				</tr>
				<tr>
					<?php writeCombinedRowHeader('Gas', 'Heat', 'Toxin') ?>
					<td><span style="color: red;">-25%</span></td>
					<td><span style="color: red;">-50%</span></td>
					<td></td>
					<td><span style="color: green;">+75%</span></td>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><sup>4)</sup>Toxin AoE</td>
				</tr>
				<tr>
					<?php writeCombinedRowHeader('Magnetic', 'Cold', 'Electricity') ?>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+75%</span></td>
					<td><span style="color: green;">+75%</span></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td> -Max Shields</td>
				</tr>
				<tr>
					<?php writeCombinedRowHeader('Radiation', 'Heat', 'Electricity') ?>
					<td></td>
					<td></td>
					<td><span style="color: red;">-75%</span></td>
					<td><span style="color: red;">-50%</span></td>
					<td></td>
					<td><span style="color: green;">+50%</span></td>
					<td></td>
					<td><span style="color: green;">+25%</span></td>
					<td></td>
					<td><span style="color: red;">-25%</span></td>
					<td></td>
					<td></td>
					<td><span style="color: green;">+75%</span></td>
					<td>-Accuracy<br><sup>1)</sup>Friendly Fire</td>
				</tr>
				<tr>
					<?php writeCombinedRowHeader('Viral', 'Cold', 'Toxin') ?>
					<td><span style="color: green;">+50%</span></td>
					<td><span style="color: green;">+75%</span></td>
					<td></td>
					<td><span style="color: red;">-50%</span></td>
					<td></td>
					<td></td>
					<td><span style="color: red;">-25%</span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>-Max Health</td>
				</tr>
			</tbody>
		</table>
    </body>
</html>
