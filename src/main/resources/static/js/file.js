//썸네일 미리보기.
function previewImage(input, destId) {
    const $pv = document.getElementById(destId);

    //초기화
    while ($pv.hasChildNodes()) {
        $pv.removeChild($pv.firstChild);
    }

    if (input.files) {
        const fileArr = Array.from(input.files)

        fileArr.forEach((file, index) => {
            const reader = new FileReader()

            //const $imgDiv = document.createElement("div")

            const $img = document.createElement("img")
            $img.classList.add("d-block")
            $img.classList.add("w-100")

            //$imgDiv.appendChild($img)

            reader.onload = e => {
                $img.src = e.target.result
            }

            console.log(file.name)
            reader.readAsDataURL(file)

            //$pv.appendChild($imgDiv)
            $pv.appendChild($img)
        })
    }
}