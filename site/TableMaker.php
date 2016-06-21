<?php
class TableMaker {
	private $weapons;
	
	public function __construct($weapons) {
		$this->weapons = $weapons;
	}
	
	public function write($class) {
		$this->begin($class);
		foreach ($this->weapons[0] as $key => $value) {
			$this->writeRow($key);
		}
		$this->end();
	}
	
	public function begin($class) {
		echo '<table class="'.$class.'">' . PHP_EOL;
	}
	
	public function end() {
		echo '</table>' . PHP_EOL;
	}
	
	public function writeHeaderRow($title, $col = null) {
		if ($col === null) {
			$col = $title;
		}
		echo '    <tr><th>' . $title . '</th>';
		foreach ($this->weapons as $weapon) {
			echo '<th>' . $weapon->$col . '</th>';
		}
		echo '</tr>' . PHP_EOL;
	}
	
	public function writeRow($title, $col = null) {
		if ($col === null) {
			$col = $title;
		}
		echo '    <tr><th>' . $title . '</th>';
		foreach ($this->weapons as $weapon) {
			echo '<td>' . $weapon->$col . '</td>';
		}
		echo '</tr>' . PHP_EOL;
	}
	
	public function dump() {
		echo '<pre>';
		var_dump($this->weapons);
		echo '</pre>';
	}
	
}
