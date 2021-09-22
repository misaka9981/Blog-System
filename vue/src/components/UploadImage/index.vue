<template>
  <el-input v-model="image" placeholder="图片地址">
    <el-upload slot="append" :before-upload="beforeImageUpload" :http-request="uploadImg" :show-file-list="false" action="" accept="image .test(file.type)
      if (!isImage) {
        this.$message.error('上传文件只能是图片!')
      }
      return isImage
    },
         uploadImg(data) {
      var formdata = new FormData()
      formdata.append('image', data.file)
      uploadImage(formdata).then(response => {
        this.$message.success(response.msg)
        this.image = response.data
      })
    }
  }
}
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>
