<?php

function writeRowHeader($damtype) {
	echo '<td colspan="2">' . damage($damtype) . '</td>';
}

function writeCombinedRowHeader($damtype, $component1, $component2) {
	echo '<td>' . elementaldamage($damtype) . '</td>';
	echo '<td style="white-space: nowrap;">' . elementaldamage($component1) . ' + ' . elementaldamage($component2) . '</td>';
}

function icon($uri) {
	return '<img src="icons/' . $uri . '" alt="" class="icon" /> ';
}

function damage($type) {
	$str = '<a href="http://warframe.wikia.com/wiki/Damage_2.0/' 
			. $type . '_Damage" title="' . $type . ' Damage"'
			. ' style="white-space: nowrap;">';
	switch ($type) {
		case 'Impact':
		case 'Puncture':
		case 'Slash':
			$str .= physicaldamage($type);
			break;
		case 'Cold':
		case 'Heat':
		case 'Electricity':
		case 'Toxin':
		case 'Blast':
		case 'Corrosive':
		case 'Gas':
		case 'Magnetic':
		case 'Radiation':
		case 'Viral':
			$str .= elementaldamage($type);
			break;
		default:
			$str .= $type;
			break;
	}
	return $str . '</a>';
}

function physicaldamage($type) {
	return icon(strtolower($type).'.svg') . ' ' . $type;
}

function elementaldamage($type) {
	$colors = array(
		'Cold' => '#11559A',
		'Electricity' => '#5F04B4',
		'Heat' => '#990000',
		'Toxin' => '#578808',
		'Blast' => '#B45F04',
		'Corrosive' => '#009933',
		'Gas' => '#71905E',
		'Magnetic' => '#0033CC',
		'Radiation' => '#088A85',
		'Viral' => '#886A08'
	);
	return icon(strtolower($type).'.png') . ' <span style="color:'.$colors[$type].';">' . $type . '</span>';
}