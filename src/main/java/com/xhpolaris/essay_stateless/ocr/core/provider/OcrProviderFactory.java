package com.xhpolaris.essay_stateless.ocr.core.provider;

import com.xhpolaris.essay_stateless.exception.BizException;
import com.xhpolaris.essay_stateless.exception.ECode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OcrProviderFactory {

    // 蜜蜂家校Ocr
    private final BeeOcrProvider beeOcrProvider;

    // TextinOcr
    private final TextinOcrProvider textinOcrProvider;

    public OcrProvider getOcrProvider(String provider) throws Exception {
        return switch (provider) {
            case "bee" -> beeOcrProvider;
            case "textin" -> textinOcrProvider;
            default -> throw BizException.Failed(ECode.BIZ_OCR_NON_PROVIDER);
        };
    }

//    // Lazy加载BeeOcr
//    public static BeeOcrProvider getBeeOcrProvider() {
//        if (beeOcrProvider == null) {
//            // 双重检查锁确保懒汉式单例模式的有效性
//            synchronized (BeeOcrProvider.class) {
//                if (beeOcrProvider == null) {
//                    beeOcrProvider = new BeeOcrProvider();
//                }
//            }
//        }
//        return beeOcrProvider;
//    }
//
//    // Lazy加载Textin
//    private static TextinOcrProvider getTextinOcrProvider() {
//        if (textinOcrProvider == null) {
//            // 双重检查锁确保懒汉式单例模式的有效性
//            synchronized (TextinOcrProvider.class) {
//                if (textinOcrProvider == null) {
//                    textinOcrProvider = new TextinOcrProvider();
//                }
//            }
//        }
//        return textinOcrProvider;
//    }
}

/*
Spring管理单例模式:
    Product注册为Component，Factory通过IoC注入获取Product
    问题：Bean是实例级别的，Factory无法实现静态的工厂方法，如果要用的话必须创建一个Factory实例。
手动实现单例模式：
    通过双检查锁实现懒汉式单例模式
    问题：Product需要从yaml中读取配置，如果通过Bean读配置，又绕回了Spring管理，不用的话，读取Yaml配置很麻烦
交由Product实现单例模式：
    Factory不管理单例，Product管理单例。
    问题：此时Product类需要有一个字段存单例，会导致Bean创建时循环依赖，无法创建
权衡后还是使用第一种，实现简单
 */