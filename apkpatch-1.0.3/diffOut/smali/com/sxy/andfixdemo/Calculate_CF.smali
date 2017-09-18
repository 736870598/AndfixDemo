.class public Lcom/sxy/andfixdemo/Calculate_CF;
.super Ljava/lang/Object;
.source "Calculate.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public calculateResult()I
    .locals 3
    .annotation runtime Lcom/alipay/euler/andfix/annotation/MethodReplace;
        clazz = "com.sxy.andfixdemo.Calculate"
        method = "calculateResult"
    .end annotation

    .prologue
    .line 12
    const/4 v0, 0x1

    .line 13
    .local v0, "i":I
    const/16 v1, 0xa

    .line 15
    .local v1, "j":I
    const/16 v2, 0xa

    return v2
.end method
