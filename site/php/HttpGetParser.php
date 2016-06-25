<?php

class HttpGetParser {
	private $get;
	
	public function __construct() {
		$this->get = filter_input_array(INPUT_GET);
	}
	
	public function getWeaponNames() {
		$weapons = array();		
		$selection = $this->get['weapons'];
		for ($i = 0; $i < count($selection); $i++) {
			if (preg_match('/[A-Za-z ]+/', $selection[$i]) == 1) {
				$weapons[] = $selection[$i];
			}
		}
		return $weapons;
	}
}
