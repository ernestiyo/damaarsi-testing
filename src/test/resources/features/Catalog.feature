Feature: Catalog

  Scenario: Tambah produk baru (Positive)
    Given User is on the Catalog Page
    When User clicks the "Tambah Produk" button
    And User fills all required fields with valid data:
      | Nama Katalog     | Basic Home |
      | Harga Per Meter  | 20000      |
      | Deskripsi        | Produk     |
      | Tipe Produk      | Paket      |
      | Gambar 1         | /contoh.jpg|
    And User clicks the "Simpan" button
    Then The product should appear in the catalog and user interface

  Scenario: Tambah produk baru tanpa mengisi 1 data input (Negative)
    Given User is on the Catalog Page
    When User clicks the "Tambah Produk" button
    And User fills all required fields except "Harga Per Meter" with valid data:
      | Nama Katalog     | Basic Home |
      | Harga Per Meter  | (kosong)   |
      | Deskripsi        | Produk     |
      | Tipe Produk      | Paket      |
      | Gambar 1         | /contoh.jpg|
    And User clicks the "Simpan" button
    Then User should see validation error messages

  Scenario: Tambah produk baru dengan input harga dengan nilai 0 (Negative)
    Given User is on the Catalog Page
    When User clicks the "Tambah Produk" button
    And User fills all required fields with valid data:
      | Nama Katalog     | Basic Home |
      | Harga Per Meter  | 0          |
      | Deskripsi        | Produk     |
      | Tipe Produk      | Paket      |
      | Gambar 1         | /contoh.jpg|
    And User clicks the "Simpan" button
    Then User should see validation error messages

  Scenario: Tambah produk baru dengan input harga negatif (Negative)
    Given User is on the Catalog Page
    When User clicks the "Tambah Produk" button
    And User fills all required fields with valid data:
      | Nama Katalog     | Basic Home |
      | Harga Per Meter  | -10000     |
      | Deskripsi        | Produk     |
      | Tipe Produk      | Paket      |
      | Gambar 1         | /contoh.jpg|
    And User clicks the "Simpan" button
    Then User should see validation error messages

  Scenario: Tambah produk baru dengan upload gambar dengan format tidak didukung (Negative)
    Given User is on the Catalog Page
    When User clicks the "Tambah Produk" button
    And User fills all required fields with valid data:
      | Nama Katalog     | Basic Home |
      | Harga Per Meter  | (kosong)   |
      | Deskripsi        | Produk     |
      | Tipe Produk      | Paket      |
      | Gambar 1         | /salah.txt |
    And User clicks the "Simpan" button
    Then User should see validation error messages
