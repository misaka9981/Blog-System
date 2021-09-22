(function ($) {
    $.ajaxSetup({
        contentType: "application/json;charset=UTF-8"
    });
     
    $('#search-form').submit(function (event) {
        event.preventDefault();
        var keyword = this.keyword.value;
        if (keyword === null || keyword === '') {
            $('#searchInput').focus();
        } else {
            window.location = '/search/' + keyword + '/';
        }
    })

    var panelToggle = $('.panel-toggle')
    var panelRemove = $('.panel-remove')
    panelToggle.on('click', function () {
        var that = $(this)
        var panelGal = that.parents('.panel-gal')
        if (that.hasClass('fa-chevron-circle-up')) {
            that.removeClass('fa-chevron-circle-up').addClass('fa-chevron-circle-down')
            panelGal.addClass('toggled')
        } else {
            that.removeClass('fa-chevron-circle-down').addClass('fa-chevron-circle-up')
            panelGal.removeClass('toggled')
        }
    })
    panelRemove.on('click', function () {
        var that = $(this)
        that.parents('.panel').animate({
            opacity: 0
        }, 1000, function () {
            $(this).css('display', 'none')
                     })
    })

    $(".tag-cloud-link").each(function () {
        var rand = Math.floor(Math.random() * 11 + 10);
        $(this).css("font-size", rand);
    });

    var tagsTab = $('#tags-tab')
    var friendLinksTab = $('#friend-links-tab')
    var linksTab = $('#links-tab')

    friendLinksTab.tab('show')

    tagsTab.on('click', function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    friendLinksTab.on('click', function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    linksTab.on('click', function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

     
    var goTop = $('#gal-gotop')
    goTop.css('bottom', '-40px')
    goTop.css('display', 'block')
    $(window).on('scroll', function () {
        if ($(this).scrollTop() > 200) {
            goTop.css('bottom', '20px')
        } else {
            goTop.css('bottom', '-40px')
        }
    })
    goTop.on('click', function () {
        $('body,html').animate({
            scrollTop: 0
        }, 800)
    })

     
     
    $('#up_img_file').change(function () {
        let file = this.files[0]
        const isImage = /^image\/*/.test(file.type)
        if (!isImage) {
            toastr.error('上传文件只能是图片!');
            return;
        }
        var formData = new FormData();
        formData.append('image', file)
        $.ajax({
            url: "/api/uploadImage",
            type: "POST",
            data: formData,
            async: false,             cache: false,             processData: false,             contentType: false,             dataType: "json",
            success: function (response) {
                if (response.code === 1) {
                    toastr.success(response.msg);
                    $('#show_img_file').attr('src', response.data);
                    $('#avatar').val(response.data);
                } else {
                    toastr.error(response.msg);
                }
            }
        })
        ;
    })

     
     
    $('#registerform').submit(function (event) {
        event.preventDefault();
        let formdata = $(this).serializeArray();
        let data = {};
        $(formdata).each(function (index, obj) {
            if (obj.value) {
                data[obj.name] = obj.value;
            }
        });
        $.post("api/register", JSON.stringify(data), function (response) {
            if (response.code === 1) {
                toastr.success(response.msg);
                setTimeout(function () {
                    let redirect_to = location.search + location.hash;
                    if (redirect_to) window.location = redirect_to.substring(13);
                    else window.location = '/';
                }, 1000);
            } else {
                toastr.error(response.msg);
            }
        });
    })

     
     
    $('#loginform').submit(function (event) {
        event.preventDefault();
        let formdata = $(this).serializeArray();
        let data = {};
        $(formdata).each(function (index, obj) {
            data[obj.name] = obj.value;
        });
        $.post("api/login", JSON.stringify(data), function (response) {
            if (response.code === 1) {
                toastr.success(response.msg);
                setTimeout(function () {
                    let redirect_to = location.search + location.hash;
                    if (redirect_to) window.location = redirect_to.substring(13);
                    else window.location = '/';
                }, 1000);
            } else {
                toastr.error(response.msg);
            }
        });
    })

     
     
    $(".comment-reply-link").click(function () {
        let obj = $(this).parent().parent();
        obj.after($("#respond"));
        $("#targetType").val(2);
        let parentId = obj.parents(".comment-item").attr("id");
        $("#parentId").val(parentId);
        let replyUserId = obj.parents("li").data("user-id");
        $("#replyUserId").val(replyUserId);
        $("#cancel-comment-reply-link").show();
    });

    $("#cancel-comment-reply-link").click(function () {
        $("#targetType").val(1);
        $("#parentId").val(null);
        $("#cancel-comment-reply-link").hide();
        $("#comments-content").append($("#respond"))
    });
     
     
    $('#commentform').submit(function (event) {
        event.preventDefault();
        let formdata = $(this).serializeArray();
        let data = {};
        $(formdata).each(function (index, obj) {
            if (obj.value) {
                data[obj.name] = obj.value;
            }
        });
        var content = data.content;
        if (content === null || content === '') {
            $('#content').focus();
            $('#content').attr('placeholder', '评论内容不能为空');
        } else {
            $('#comment-submit').hide();
            $('#loading').show();
            $.post("api/comment/save", JSON.stringify(data), function (response) {
                if (response.code === 1) {
                    toastr.success(response.msg);
                    setTimeout(function () {
                        location.reload()
                    }, 1000);
                } else if (response.code === 40001) {
                    toastr.error(response.msg);
                    setTimeout(function () {
                        window.location = '/login?redirect_to=' + location.href
                    }, 1000);
                } else {
                    toastr.error(response.msg);
                }
                $('#comment-submit').show();
                $('#loading').hide();
            });
        }
    })

     
     
    $("#more-comments").click(function (event) {
        let current = $(this).data("current");
        let pages = $(this).data("pages");
        let size = $(this).data("size");
        let articleId = $("#articleId").val();
        let isLogin = $(this).data("is-login");
        $(this).addClass("disabled");
        $.get("api/comment/more", {current: ++current, size: size, articleId: articleId},
            function (data) {
                for (let item of data) {
                    let li = '<li id="' + item.id + '" class="comment-item"' + 'data-user-id="' + item.user.id + '">\n';
                    li += '<article class="comment-body">\n' +
                        '<footer class="comment-meta">\n' +
                        '<div class="comment-author vcard">\n' +
                        '<img width="66" height="66" src="' + (item.user.avatar || '/images/default-avatar.jpg') + '">\n' +
                        '<b class="fn">' + item.user.nickname + '</b>\n' +
                        '<span class="says">说：</span>\n' +
                        '</div>\n' +
                        '<div class="comment-metadata">\n' +
                        '<a>\n' +
                        '<time datetime="' + item.createTime + '">' + parseTime(item.createTime) + '</time>\n' +
                        '</a>\n' +
                        '</div>\n' +
                        '</footer>\n' +
                        '<div class="comment-content">\n' +
                        '<p>' + item.content + '</p>\n' +
                        '</div>\n' +
                        '<div class="reply">\n';
                    if (isLogin) {
                        li += '<a rel="nofollow" class="comment-reply-link">回复</a>';
                    } else {
                        li += '<a rel="nofollow" class="comment-reply-login" href="/login?redirect_to=' + location.href + '#' + item.id + '">登录以回复</a>';
                    }
                    li += '</div>\n' +
                        '</article>\n';
                                         if (item.subComments && item.subComments.length > 0) {
                        li += '<ol class="children" style="padding-left: 20px;">\n';
                        for (let reply of item.subComments) {
                            li += '<li id="' + reply.id + '" data-user-id="' + reply.user.id + '">\n' +
                                '<article class="comment-body comment-children">\n' +
                                '<footer class="comment-meta">\n' +
                                '<div class="comment-author vcard">\n' +
                                '<img width="66" height="66" src="' + (reply.user.avatar || '/images/default-avatar.jpg') + '">\n' +
                                '<b class="fn">' + reply.user.nickname + '</b>\n' +
                                ' 回复 <b class="fn">' + reply.replyUser.nickname + '</b>\n' +
                                '<span class="says">说：</span>\n' +
                                '</div>\n' +
                                '<div class="comment-metadata">\n' +
                                '<time datetime="' + item.createTime + '">' + parseTime(item.createTime) + '</time>\n' +
                                '</div>\n' +
                                '</footer>\n' +
                                '<div class="comment-content">\n' +
                                '<p>' + reply.content + '</p>\n' +
                                '</div>\n' +
                                '<div class="reply">\n';
                            if (isLogin) {
                                li += '<a rel="nofollow" class="comment-reply-link">回复</a>';
                            } else {
                                li += '<a rel="nofollow" class="comment-reply-login" href="/login?redirect_to=' + location.href + '#' + reply.id + '">登录以回复</a>';
                            }
                            li += '</div>\n' +
                                '</article>\n' +
                                '</li>\n';
                        }
                        li += '</ol>';
                    }
                    li += "</li>\n";
                    $('#comment-list').append(li);
                }
            });
        $(this).data("current", current);
        if (pages <= current) {
            $(this).hide();
        } else {
            $(this).removeClass("disabled");
        }
    });

     })($)

function parseTime(time, cFormat) {
    if (arguments.length === 0) {
        return null
    }
    const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
    let date
    if (typeof time === 'object') {
        date = time
    } else {
        if (('' + time).length === 10) time = parseInt(time) * 1000
        date = new Date(time)
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    }
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key]
                 if (key === 'a') {
            return ['日', '一', '二', '三', '四', '五', '六'][value]
        }
        if (result.length > 0 && value < 10) {
            value = '0' + value
        }
        return value || 0
    })
    return time_str
}
