var that;
class Tab {
    constructor(id) {
        that = this;
        //  获取元素
        this.main = document.querySelector(id);
        this.add = this.main.querySelector('.tabadd');
        // li的父元素
        this.ul = this.main.querySelector('.fisrstnav ul:first-child')
        // section的父元素
        this.fsection = this.main.querySelector('.tabscon');
        this.init();
    }
    // 初始化的类
    init() {
        this.updateNode();
        this.add.onclick = this.addTab;
        for (var i = 0; i < this.lis.length; i++) {
            this.lis[i].index = i;
            this.lis[i].onclick = this.toggleTab;
            this.remove[i].onclick = this.removeTab;
            this.spans[i].ondblclick = this.editTab;
            this.sections[i].ondblclick = this.editTab;
        }
    }
    // 获取所有的li和section
    updateNode() {
        this.lis = this.main.querySelectorAll('li');
        this.sections = this.main.querySelectorAll('section');
        // 获取×号   因为动态添加元素 需要重新获取对应的元素
        this.remove = this.main.querySelectorAll('.icon-guanbi')
        this.spans = this.main.querySelectorAll('.fisrstnav li span:first-child');
    }
    // 切换
    toggleTab() {
        that.clearClass();
        this.className = 'liactive';
        that.sections[this.index].className = 'conactive';
    }
    // 清除所有li和section类
    clearClass() {
        for (var i = 0; i < this.lis.length; i++) {
            this.lis[i].className = '';
            this.sections[i].className = '';
        }
    }
    // 添加
    addTab() {
        that.clearClass();
        //   1.创建li元素和section元素
        var random = Math.random();
        var li = '<li class="liactive"><span>新选项卡</span><span class="iconfont icon-guanbi"></span></li>'
        var section = '<section class="conactive"> 测试卡' + random + '</section>';
        // 2.把这两个元素追加到对应的父元素里面
        that.ul.insertAdjacentHTML('beforeend', li);
        that.fsection.insertAdjacentHTML('beforeend', section);
        that.init();
    }
    // 删除
    removeTab(e) {
        e.stopPropagation();//阻止冒泡 防止触发li的切换事件
        var index = this.parentNode.index;
        // 根据索引号删除对应的li和section  remove()方法可以直接删除指定的元素
        that.lis[index].remove();
        that.sections[index].remove();
        that.init();
        //当我们删除了不是选定状态这个li之后  让他原来的li处于选定状态
        if (document.querySelector('.liactive')) return;
        //当我们删除了选定状态这个li之后  让他前一个li处于选定状态
        index--;
        // 手动调用我们的点击事件  不需要鼠标触发
        that.lis[index] && that.lis[index].click();
    }
    // 修改
    editTab() {
        var str = this.innerHTML;
        // 双击禁止选定文字
        window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
        this.innerHTML = '<input type="text"/>';
        var input = this.children[0];
        input.value = str;
        input.select();//文本框的内容处于选定状态
        input.onblur = function () {
            this.parentNode.innerHTML = this.value;
        }
        input.onkeyup = function (e) {
            if (e.keyCode === 13) {
                this.blur();//手动调用表单失去焦点事件  不需要鼠标离开操作
            }
        }
    }
}
new Tab('#tab');
