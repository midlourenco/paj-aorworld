import React from 'react'

export default function auxiliar() {
	console.log("auxliar")
}

/**função seguinte vem do proj4 PAJ 2021/2022 */
/**
 * Função que valida se o url é de uma imagem, verificando o tamanho da imagem no
 * @param {*} url
 * @returns
 */
 export const isImage=(url)=>{
	let image = new Image();

	image.onload = function () {
		if (this.width > 0) {
			console.log("image exists");
            return url
			//picElement.src = sessionStorage.getItem("picURL");
			//document.querySelector(".user-pic").setAttribute("src",  sessionStorage.getItem("picURL"));
		} else {
			//picElement.src = "images/logo.png";
            return "images/logo.png";
		}
	};
	image.onerror = function () {
		console.log("image doesn't exist");
		// picElement.src = "images/logo.png";
        return "images/logo.png";
	};
	image.src = url;
}


// export default auxiliar
