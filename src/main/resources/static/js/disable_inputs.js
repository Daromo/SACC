/**
 * Author: Rojas Morales Jose Daniel
 * Deshabilitar los inputs de acuerdo al regimen fiscal seleccionado
 */
 
$(function() {
	$("#regimenFiscalId").change(function() {
		if ($(this).val() === "601") {
			$("#razonSocial").prop("disabled", false);
			$("#nombreCliente").prop("disabled", true);
			$("#apPaternoCliente").prop("disabled", true);
			$("#apMaternoCliente").prop("disabled", true);
			$('#nombreCliente').val('');
			$('#apPaternoCliente').val('');
			$('#apMaternoCliente').val('');
		} else {
			$("#nombreCliente").prop("disabled", false);
			$("#apPaternoCliente").prop("disabled", false);
			$("#apMaternoCliente").prop("disabled", false);
			$("#razonSocial").prop("disabled", true);
			$('#razonSocial').val('');
		}
	});
});