<template>
  <div class="upload-container">
    <el-upload :before-upload="beforeImageUpload" :http-request="uploadImg" :show-file-list="false" action="" accept="image .test(file.type)
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
        this.$emit('success', response.data)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.editor-slide-upload {
  margin-bottom: 20px;
  /deep/ .el-upload--picture-card {
    width: 100%;
  }
}
</style>
