##### 学习网址 https://blog.csdn.net/weixin_38405253/article/details/103453495

# lamdba？
- JavaSE 8的新特性
- 允许通过表达式代替功能接口
- 与方法一样，
  - 提供参数列表 
  - 提供使用参数的主体(主体可以理解为表达式或代码块)
  - 增强了集合库

# lamdba语法 (参考语法包)
- 语法要求：必须有相应的函数接口，即不能在代码任何地方任性的写lamdba表达式
- lamdba的类型对应的就是函数接口的类型。
- 通过上下文信息编译器推断出参数表的类型，而不需要显式命名

# 自定义接口　(自定义函数接口包)

# 内置四大核心函数式接口
- 函数式接口                参数类型   返回类型  用途
- Consumer<T>消费型接口     T         void    对类型为T的对象应用操作，包含方法void accept(T t)
- Supplier<T>供给型接口     无        T       返回类型为T的对象，包含方法 T get()
- Function<T,R>函数式接口   T         R       对类型为T的对象应用操作，并返回结果，结果为R类型的对象。包含方法 R apply(T t)
- Predicate<T>断定型接口    T         boolean 确定类型为T的对象是否满足某约束，并返回Boolean值。包含方法boolean test(T,t)

