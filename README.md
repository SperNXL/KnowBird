# KnowBird

中文名：知鸟

## 部署问题
`The project is using an incompatible version (AGP 9.0.0) of the Android Gradle plugin. 
Lastest supported version is AGP 8.13.0`

该问题进入build_gradle(KnowBird), 点击进入application，将 agp = "9.0.0" 更改为 agp = "8.13.0"

## OCR 配置
需要将以下配置放入 local.properties中
```xml
KuaiTongOCR_ACCESS_KEY=replace_with_your_access_key
KuaiTongOCR_ACCESS_SECRET=replace_with_your_access_secret
# expires_in 7 days
KuaiTongOCR_Token=replace_with_your_token
