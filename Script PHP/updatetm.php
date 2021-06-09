<?php
	$server		= "localhost";
	$user		= "root";
	$password	= "";
	$namadb		= "tiumy";

	$conn = mysqli_connect($server, $user, $password, $namadb) or die ("Koneksi gagal");
		
	$id = $_POST ['id'];
	$nama = $_POST ['nama'];
	$telpon = $_POST ['telpon'];

	class emp{}

	if (empty($nama) || empty($telpon)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Data tidak boleh kosong";
		die(json_encode($response));
	}else {
		$query = mysqli_query($conn, "UPDATE teman SET nama = '".$nama."', telpon = '".$telpon."' WHERE id = '".$id."' ");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil diedit";
			die(json_encode($response));
		}else {
			$response = new emp();
			$response->success = 0;
			$response->message = "Gagal mengedit data";
			die(json_encode($response));
		}
		
	}

?>
