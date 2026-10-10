package com.example.woofon.network

import android.content.Context
import android.content.res.AssetManager
import net.schmizz.sshj.SSHClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.File
import java.security.Security

suspend fun shutdownComputer(
    host: String,
    username: String,
    port: Int = 22,
    context: Context,
    fileName: String
) {
    withContext(Dispatchers.IO) {
        val privateKeyPath = getAssetsFile(context, fileName)

        SSHClient().use { ssh ->
            Security.removeProvider("BC")
            Security.insertProviderAt(BouncyCastleProvider(), 1)

            ssh.loadKnownHosts(getAssetsFile(context, "known_hosts"))
            ssh.connect(host, port)
//            ssh.authPublickey(username, ssh.loadKeys(privateKeyPath.absolutePath))
            ssh.authPassword(username, "matvey2008MMM1827")
            ssh.startSession().use { session ->
                val command = session.exec("shutdown /s /t 0")
                command.join()

                check(command.exitStatus == 0) {
                    "Remote shutdown command failed with exit code ${command.exitStatus}"
                }
            }
        }
    }
}

private fun getAssetsFile(context: Context, fileName: String): File {
    val file = File(context.cacheDir, fileName)

    if (!file.exists()) {
        context.assets.open(fileName).use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
    return file
}
